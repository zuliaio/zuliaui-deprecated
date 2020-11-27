package io.zulia.ui.client.views.home;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.i18n.client.NumberFormat;
import elemental2.core.JsArray;
import elemental2.core.JsDate;
import io.zulia.ui.client.dto.MemberDTO;
import io.zulia.ui.client.services.ServiceProvider;
import io.zulia.ui.client.util.NotifyUtil;
import us.ascendtech.highcharts.client.ChartOptions;
import us.ascendtech.highcharts.client.Highcharts;
import us.ascendtech.highcharts.client.chartoptions.Title;
import us.ascendtech.highcharts.client.chartoptions.axis.Axis;
import us.ascendtech.highcharts.client.chartoptions.axis.AxisPlotLine;
import us.ascendtech.highcharts.client.chartoptions.axis.AxisTitle;
import us.ascendtech.highcharts.client.chartoptions.axis.XAxis;
import us.ascendtech.highcharts.client.chartoptions.axis.YAxis;
import us.ascendtech.highcharts.client.chartoptions.chart.Chart;
import us.ascendtech.highcharts.client.chartoptions.chart.ChartEvents;
import us.ascendtech.highcharts.client.chartoptions.chart.functions.Load;
import us.ascendtech.highcharts.client.chartoptions.series.Series;
import us.ascendtech.highcharts.client.chartoptions.series.SeriesData;
import us.ascendtech.highcharts.client.chartoptions.shared.SeriesTypes;

@Component(components = { MemberCardComponent.class })
public class HomeComponent implements IsVueComponent, HasBeforeMount {

	private NumberFormat numberFormat = NumberFormat.getDecimalFormat().overrideFractionDigits(2);
	private Highcharts splineChart;

	@Data
	ChartOptions splineChartOptions;

	@Data
	JsArray<MemberDTO> members;

	@Data
	String usedDataDirSpaceGBFormatted;

	@Data
	String freeDataDirSpaceGBFormatted;

	@Data
	String totalDataDirSpaceGBFormatted;

	@Override
	public void beforeMount() {
		ServiceProvider.get().getService().getStats().subscribe(statsDTO -> {

			splineChartOptions = new ChartOptions();
			Load load = () -> Scheduler.get().scheduleFixedDelay(() -> {
				ServiceProvider.get().getService().getStats().subscribe(statsDTO1 -> {
					Double value = statsDTO1.getJvmUsedMemoryMB();
					Series series = splineChart.getSeries().getAt(0);
					JsArray<Object> data = new JsArray<>(JsDate.now(), value);
					if (series.getData().length == 30) {
						series.addPoint(data, true, true, true, false);
					}
					else {
						series.addPoint(data, true, false, true, false);
					}
				}, NotifyUtil::handleError);
				return true;
			}, 1500);
			ChartEvents chartEvents = new ChartEvents();
			chartEvents.setLoad(load);
			splineChartOptions.setChart(new Chart().setType(SeriesTypes.SPLINE.getSeriesType()).setEvents(chartEvents));

			Axis xAxis = new XAxis().setType("datetime").setTickPixelInterval(150);
			splineChartOptions.setxAxis((XAxis) xAxis);
			JsArray<AxisPlotLine> plotLines = new JsArray<>();
			plotLines.push(new AxisPlotLine().setValue(0).setWidth(1).setColor("#808080"));
			Axis yAxis = new YAxis().setTitle(new AxisTitle().setText("Memory Usage")).setPlotLines(plotLines);
			splineChartOptions.setyAxis((YAxis) yAxis);

			Series splineSeries = new Series();
			JsArray<Object> splineData = new JsArray<>();
			splineSeries.setName("Memory Usage MB");

			splineData.push(new SeriesData().setX(JsDate.now()).setY(statsDTO.getJvmUsedMemoryMB()));
			splineChartOptions.setSeries(new JsArray<>(splineSeries));
			splineChartOptions.setTitle(new Title().setText("Memory Usage MB"));

			splineSeries.setData(splineData);

			splineChart = Highcharts.chart("memoryPieChart", splineChartOptions);

			usedDataDirSpaceGBFormatted = numberFormat.format(statsDTO.getUsedDataDirSpaceGB()) + "GB";
			freeDataDirSpaceGBFormatted = numberFormat.format(statsDTO.getFreeDataDirSpaceGB()) + "GB";
			totalDataDirSpaceGBFormatted = numberFormat.format(statsDTO.getTotalDataDirSpaceGB()) + "GB";

		}, NotifyUtil::handleError);

		members = new JsArray<>();
		ServiceProvider.get().getService().getMembers().subscribe(members -> {
			this.members = members.getMembers();
		}, NotifyUtil::handleError);
	}

}
