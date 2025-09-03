package com.wfrocha.movieflix.controller;

import com.wfrocha.movieflix.controller.request.StreamingRequest;
import com.wfrocha.movieflix.controller.response.StreamingResponse;
import com.wfrocha.movieflix.entity.Streaming;
import com.wfrocha.movieflix.mapper.StreamingMapper;
import com.wfrocha.movieflix.service.StreamingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movieflix/streaming")
@RequiredArgsConstructor
public class StreamingController {

    private final StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> findAll() {
        List<StreamingResponse> streamings =
                StreamingMapper.toStreamingResponseList(streamingService.findAll());
        return ResponseEntity.ok(streamings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse> findById(@PathVariable int id) {
        return streamingService.findById(id)
                .map(StreamingMapper::toStreamingResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StreamingResponse> save(@RequestBody StreamingRequest request) {
        Streaming streaming = StreamingMapper.toStreaming(request);
        Streaming savedStreaming = streamingService.save(streaming);
        StreamingResponse response = StreamingMapper.toStreamingResponse(savedStreaming);

        URI location = URI.create("/movieflix/streaming/" + savedStreaming.getId());
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StreamingResponse> update(@PathVariable long id,
                                                    @RequestBody StreamingRequest request) {
        Streaming streaming = StreamingMapper.toStreaming(request);
        streaming.setId(id);
        Streaming updatedStreaming = streamingService.update(streaming);
        return ResponseEntity.ok(StreamingMapper.toStreamingResponse(updatedStreaming));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        streamingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

