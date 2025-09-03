package com.wfrocha.movieflix.mapper;

import com.wfrocha.movieflix.controller.request.StreamingRequest;
import com.wfrocha.movieflix.controller.response.StreamingResponse;
import com.wfrocha.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class StreamingMapper {

    public static Streaming toStreaming(StreamingRequest request) {
        return Streaming.builder()
                .name(request.name())
                .build();
    }

    public static StreamingResponse toStreamingResponse(Streaming request) {
        return StreamingResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
    }

    public static List<StreamingResponse> toStreamingResponseList(List<Streaming> streamings) {
        return streamings.stream()
                .map(StreamingMapper::toStreamingResponse)
                .collect(Collectors.toList());
    }

}
