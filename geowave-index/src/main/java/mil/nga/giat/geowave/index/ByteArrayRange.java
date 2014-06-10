package mil.nga.giat.geowave.index;


/***
 * Defines a unit interval on a number line
 * 
 */
public class ByteArrayRange
{
	private final ByteArrayId start;
	private final ByteArrayId end;

	/***
	 * 
	 * @param start
	 *            start of unit interval
	 * @param end
	 *            end of unit interval
	 */
	public ByteArrayRange(
			final ByteArrayId start,
			final ByteArrayId end ) {
		this.start = start;
		this.end = end;
	}

	public ByteArrayId getStart() {
		return start;
	}

	public ByteArrayId getEnd() {
		return end;
	}

	public boolean isSingleValue() {
		return (start != null) && (end != null) && (start.equals(end));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((end == null) ? 0 : end.hashCode());
		result = (prime * result) + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(
			final Object obj ) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ByteArrayRange other = (ByteArrayRange) obj;
		if (end == null) {
			if (other.end != null) {
				return false;
			}
		}
		else if (!end.equals(other.end)) {
			return false;
		}
		if (start == null) {
			if (other.start != null) {
				return false;
			}
		}
		else if (!start.equals(other.start)) {
			return false;
		}
		return true;
	}
}
