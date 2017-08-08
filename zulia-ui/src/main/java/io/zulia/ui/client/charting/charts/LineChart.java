package io.zulia.ui.client.charting.charts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayMixed;
import io.zulia.ui.client.charting.Highcharts;
import io.zulia.ui.client.charting.handlers.ChartDataSelectionHandler;
import io.zulia.ui.client.charting.options.ChartOptions;
import io.zulia.ui.client.charting.options.ChartType;
import io.zulia.ui.client.charting.options.CursorType;
import io.zulia.ui.client.charting.options.ExportingOptions;
import io.zulia.ui.client.charting.options.HighchartsOptions;
import io.zulia.ui.client.charting.options.PlotOptions;
import io.zulia.ui.client.charting.options.PointOptions;
import io.zulia.ui.client.charting.options.Series;
import io.zulia.ui.client.charting.options.SeriesEventOptions;
import io.zulia.ui.client.charting.options.SeriesOptions;
import io.zulia.ui.client.charting.options.TitleOptions;
import io.zulia.ui.client.charting.options.XAxisOptions;
import io.zulia.ui.client.charting.options.YAxisOptions;
import io.zulia.ui.client.charting.options.YAxisTitle;
import io.zulia.ui.client.charting.selection.handler.DataSelectionHandler;

/**
 * Created by Payam Meyer on 3/22/2016.
 * @author pmeyer
 */
public class LineChart extends Highcharts implements ChartDataSelectionHandler {
	public static LCBuilder getBuilder() {
		return new LCBuilder();
	}

	public static class LCBuilder extends ChartBuilder {

		public LineChart build() {
			HighchartsOptions options = HighchartsOptions.create();

			ChartOptions chartOptions = ChartOptions.create();
			chartOptions.setChartType(ChartType.LINE);
			options.setChart(chartOptions);

			JsArrayMixed seriesArray = JsArrayMixed.createArray().cast();
			Series series = Series.create();
			JsArrayMixed dataList = JsArrayMixed.createArray().cast();
			for (String key : data.keySet()) {
				dataList.push((Integer) data.get(key));
			}
			series.setData(dataList);
			series.setShowInLegend(false);
			seriesArray.push(series);
			options.setSeries(seriesArray);

			XAxisOptions xAxis = XAxisOptions.create();
			xAxis.setCategories(data.keySet());
			xAxis.setAllowDecimals(super.xAxisAllowDecimals);
			options.setXAxis(xAxis);

			YAxisOptions yAxis = YAxisOptions.create();
			yAxis.setTitle(YAxisTitle.create().setText("Count"));
			yAxis.setMin(0);
			options.setYAxis(yAxis);
			yAxis.setAllowDecimals(super.yAxisAllowDecimals);

			if (null != colors) {
				options.setColors(colors);
			}

			options.setTitle(TitleOptions.create().setText(chartTitle));

			options.setExporting(ExportingOptions.create());
			options.getExporting().setURL(GWT.getHostPageBaseURL() + "highcharts/export");
			options.getExporting().setFilename(chartTitle);
			options.getExporting().setEnabled(true);

			LineChart lc = new LineChart(height);

			if (null != handler) {
				PlotOptions plotOptions = PlotOptions.create();
				SeriesOptions seriesOptions = SeriesOptions.create();
				seriesOptions.setPoint(PointOptions.create().setEvents(SeriesEventOptions.create().setClick(lc)));
				seriesOptions.setCursor(CursorType.POINTER);
				plotOptions.setSeries(seriesOptions);
				options.setPlotOptions(plotOptions);
				lc.setSelectionHandler(handler);
			}

			lc.setHighchartOptions(options);
			lc.setDataSource(dataSource);

			return lc;
		}
	}

	private DataSelectionHandler selectionHandler;
	private String dataSource;

	public LineChart(Integer height) {
		super(height);
	}

	public void setSelectionHandler(DataSelectionHandler selectionHandler) {
		this.selectionHandler = selectionHandler;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public boolean onClick(String x, Integer y) {
		GWT.log("dataSource: " + dataSource + " Category: " + x + " :: " + y);
		if (null != selectionHandler)
			selectionHandler.handleDataSelection(dataSource, x, y);
		return false;
	}

}
