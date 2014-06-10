package mil.nga.giat.geowave.index.sfc.data;

import org.junit.Assert;
import org.junit.Test;

public class BasicNumericDatasetTest
{

	private double DELTA = 1e-15;

	private BasicNumericDataset basicNumericDatasetRanges = new BasicNumericDataset(
			new NumericData[] {
				new NumericRange(
						10,
						50),
				new NumericRange(
						25,
						95),
				new NumericRange(
						-50,
						50)
			});

	private BasicNumericDataset basicNumericDatasetValues = new BasicNumericDataset(
			new NumericData[] {
				new NumericValue(
						25),
				new NumericValue(
						60),
				new NumericValue(
						0)
			});

	@Test
	public void testNumericRangesMinValues() {

		int expectedCount = 3;
		double[] expectedMinValues = new double[] {
			10,
			25,
			-50
		};
		double[] mins = basicNumericDatasetRanges.getMinValuesPerDimension();

		Assert.assertEquals(
				expectedCount,
				basicNumericDatasetRanges.getDimensionCount());

		for (int i = 0; i < basicNumericDatasetRanges.getDimensionCount(); i++) {
			Assert.assertEquals(
					expectedMinValues[i],
					mins[i],
					DELTA);
		}

	};

	@Test
	public void testNumericRangesMaxValues() {

		int expectedCount = 3;
		double[] expectedMaxValues = new double[] {
			50,
			95,
			50
		};
		double[] max = basicNumericDatasetRanges.getMaxValuesPerDimension();

		Assert.assertEquals(
				expectedCount,
				basicNumericDatasetRanges.getDimensionCount());

		for (int i = 0; i < basicNumericDatasetRanges.getDimensionCount(); i++) {
			Assert.assertEquals(
					expectedMaxValues[i],
					max[i],
					DELTA);
		}
	};

	@Test
	public void testNumericRangesCentroidValues() {
		
		int expectedCount = 3;
		double[] expectedCentroidValues = new double[] {
			30,
			60,
			0
		};
		double[] centroid = basicNumericDatasetRanges.getCentroidPerDimension();

		Assert.assertEquals(
				expectedCount,
				basicNumericDatasetRanges.getDimensionCount());

		for (int i = 0; i < basicNumericDatasetRanges.getDimensionCount(); i++) {
			Assert.assertEquals(
					expectedCentroidValues[i],
					centroid[i],
					DELTA);
		}
		
	};
	
	@Test
	public void testNumericValuesAllValues() {

		int expectedCount = 3;
		
		double[] expectedValues = new double[] {
			25,
			60,
			0
		};
		
		double[] mins = basicNumericDatasetValues.getMinValuesPerDimension();
		double[] max = basicNumericDatasetValues.getMaxValuesPerDimension();
		double[] centroid = basicNumericDatasetValues.getCentroidPerDimension();

		Assert.assertEquals(
				expectedCount,
				basicNumericDatasetValues.getDimensionCount());

		for (int i = 0; i < basicNumericDatasetValues.getDimensionCount(); i++) {
			Assert.assertEquals(
					expectedValues[i],
					mins[i],
					DELTA);
			Assert.assertEquals(
					expectedValues[i],
					max[i],
					DELTA);
			Assert.assertEquals(
					expectedValues[i],
					centroid[i],
					DELTA);
		}

	};

}
