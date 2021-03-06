package com.github.dreamhead.moco.runner;

import com.github.dreamhead.moco.Moco;
import com.github.dreamhead.moco.MocoConfig;
import com.github.dreamhead.moco.parser.model.RequestSetting;
import com.github.dreamhead.moco.parser.model.ResponseSetting;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import java.io.InputStream;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static com.google.common.base.Optional.of;

public final class RunnerSetting {
    private final ImmutableList<InputStream> streams;
    private final Optional<RequestSetting> request;
    private final Optional<ResponseSetting> response;
    private final Optional<String> context;
    private final Optional<String> fileRoot;

    private RunnerSetting(final ImmutableList<InputStream> streams, final String context, final String fileRoot,
                          final RequestSetting requestSetting, final ResponseSetting response) {
        this.streams = streams;
        this.request = fromNullable(requestSetting);
        this.response = fromNullable(response);
        this.context = fromNullable(context);
        this.fileRoot = fromNullable(fileRoot);
    }

    public ImmutableList<InputStream> getStreams() {
        return streams;
    }

    public Optional<MocoConfig> context() {
        if (context.isPresent()) {
            return of(Moco.context(context.get()));
        }

        return absent();
    }

    public Optional<MocoConfig> fileRoot() {
        if (fileRoot.isPresent()) {
            return of(Moco.fileRoot(fileRoot.get()));
        }

        return absent();
    }

    public Optional<MocoConfig> request() {
        if (request.isPresent()) {
            return of(Moco.request(request.get().getRequestMatcher()));
        }

        return absent();
    }

    public Optional<MocoConfig> response() {
        if (response.isPresent()) {
            return of(Moco.response(response.get().getResponseHandler()));
        }

        return absent();
    }

    public static Builder aRunnerSetting() {
        return new Builder();
    }

    public static class Builder {
        private ImmutableList.Builder<InputStream> streams = ImmutableList.builder();
        private RequestSetting request;
        private ResponseSetting response;
        private String context;
        private String fileRoot;

        public final Builder addStream(final InputStream stream) {
            this.streams.add(stream);
            return this;
        }

        public final Builder addStreams(final ImmutableList<InputStream> streams) {
            this.streams.addAll(streams);
            return this;
        }

        public final Builder withRequest(final RequestSetting request) {
            this.request = request;
            return this;
        }

        public final Builder withResponse(final ResponseSetting response) {
            this.response = response;
            return this;
        }

        public final Builder withContext(final String context) {
            this.context = context;
            return this;
        }

        public final Builder withFileRoot(final String fileRoot) {
            this.fileRoot = fileRoot;
            return this;
        }

        public final RunnerSetting build() {
            return new RunnerSetting(streams.build(), context, fileRoot, request, response);
        }

    }
}
