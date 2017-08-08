package io.zulia.ui.client.charting.handlers;

import io.zulia.ui.client.charting.options.Series;

public interface TooltipFormatter {

	String format(String x, String y, Series series);
}
