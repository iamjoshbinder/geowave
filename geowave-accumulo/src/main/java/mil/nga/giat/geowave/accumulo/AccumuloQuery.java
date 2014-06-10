package mil.nga.giat.geowave.accumulo;

import java.util.List;

import mil.nga.giat.geowave.index.ByteArrayId;
import mil.nga.giat.geowave.index.ByteArrayRange;
import mil.nga.giat.geowave.index.StringUtils;
import mil.nga.giat.geowave.store.index.Index;

import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.client.Scanner;
import org.apache.accumulo.core.client.ScannerBase;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.core.data.Range;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;

/**
 * This class is used internally to perform query operations against an Accumulo
 * data store. The query is defined by the set of parameters passed into the
 * constructor.
 */
abstract public class AccumuloQuery
{
	private final static Logger LOGGER = Logger.getLogger(AccumuloQuery.class);
	protected final List<ByteArrayId> adapterIds;
	protected final Index index;

	public AccumuloQuery(
			final Index index ) {
		this(
				null,
				index);
	}

	public AccumuloQuery(
			final List<ByteArrayId> adapterIds,
			final Index index ) {
		this.adapterIds = adapterIds;
		this.index = index;
	}

	abstract protected List<ByteArrayRange> getRanges();

	protected ScannerBase getScanner(
			final AccumuloOperations accumuloOperations,
			final Integer limit ) {
		final List<ByteArrayRange> ranges = getRanges();
		final String tableName = StringUtils.stringFromBinary(index.getId().getBytes());
		ScannerBase scanner;
		try {
			if ((ranges != null) && (ranges.size() == 1)) {
				scanner = accumuloOperations.createScanner(tableName);
				final ByteArrayRange r = ranges.get(0);
				if (r.isSingleValue()) {
					((Scanner) scanner).setRange(Range.exact(new Text(
							r.getStart().getBytes())));
				}
				else {
					((Scanner) scanner).setRange(byteArrayRangeToAccumuloRange(r));
				}
				if (limit != null && limit > 0 && limit < ((Scanner) scanner).getBatchSize()){
					((Scanner) scanner).setBatchSize(limit);
				}
			}
			else {
				scanner = accumuloOperations.createBatchScanner(tableName);
				((BatchScanner) scanner).setRanges(AccumuloUtils.byteArrayRangesToAccumuloRanges(ranges));
			}
		}
		catch (final TableNotFoundException e) {
			LOGGER.warn(
					"Unable to query table '" + tableName + "'.  Table does not exist.",
					e);
			return null;
		}
		// we are assuming we always have to ensure no duplicates
		// and that the deduplication is the least expensive filter so we add it
		// first
		if ((adapterIds != null) && !adapterIds.isEmpty()) {
			for (final ByteArrayId adapterId : adapterIds) {
				scanner.fetchColumnFamily(new Text(
						adapterId.getBytes()));
			}
		}
		return scanner;
	}

	protected static Range byteArrayRangeToAccumuloRange(
			final ByteArrayRange byteArrayRange ) {
		final Text start = new Text(
				byteArrayRange.getStart().getBytes());
		final Text end = new Text(
				byteArrayRange.getEnd().getBytes());
		if (start.compareTo(end) > 0) {
			return null;
		}
		return new Range(
				new Text(
						byteArrayRange.getStart().getBytes()),
				Range.followingPrefix(new Text(
						byteArrayRange.getEnd().getBytes())));
	}

}
