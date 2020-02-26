package io.zulia.ui.client.services;

import com.intendia.gwt.autorest.client.RequestResourceBuilder;
import com.intendia.gwt.autorest.client.RequestResponseException;
import elemental2.core.Global;
import elemental2.dom.XMLHttpRequest;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import jsinterop.base.Js;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class CustomAutoREST extends RequestResourceBuilder {

	private static final Function<XMLHttpRequest, RequestResponseException.FailedStatusCodeException> DEFAULT_UNEXPECTED_MAPPER = xhr -> new RequestResponseException.FailedStatusCodeException(
			xhr.status, xhr.responseText);

	public CustomAutoREST() {
		unexpectedMapper(DEFAULT_UNEXPECTED_MAPPER);
	}

	@Override
	public <T> T as(Class<? super T> container, Class<?> type) {
		if (Completable.class.equals(container))
			return (T) request().toCompletable();
		if (Maybe.class.equals(container))
			return (T) request().flatMapMaybe(ctx -> {
				@Nullable Object decode = decode(ctx);
				return decode == null ? Maybe.empty() : Maybe.just(decode);
			});
		if (Single.class.equals(container))
			return (T) request().map(ctx -> {
				@Nullable Object decode = decode(ctx);
				return requireNonNull(decode, "null response forbidden, use Maybe instead");
			});
		if (Observable.class.equals(container))
			return (T) request().toObservable().flatMapIterable(ctx -> {
				@Nullable Object[] decode = decode(ctx);
				return decode == null ? Collections.emptyList() : Arrays.asList(decode);
			});
		throw new UnsupportedOperationException("unsupported type " + container);
	}

	private @Nullable
	<T> T decode(XMLHttpRequest ctx) {
		try {
			String text = ctx.response.asString();
			if (text != null && !text.isEmpty()) {
				return Js.cast(Global.JSON.parse(text));
			}
			return null;
		}
		catch (Throwable e) {
			if (ctx.response.isString()) {
				return Js.cast(ctx.response.asString());
			}
			else {
				try {
					Js.cast(Global.JSON.parse(ctx.response.asString()));
				}
				catch (Throwable e2) {
					throw new RequestResponseException.ResponseFormatException("Parsing response error", e);
				}
			}
			throw new RequestResponseException.ResponseFormatException("Parsing response error", e);
		}
	}
}