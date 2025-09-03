package com.wfrocha.movieflix.service;

import com.wfrocha.movieflix.entity.Streaming;
import com.wfrocha.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StreamingService {

    private final StreamingRepository streamingRepository;

    public List<Streaming> findAll() {
        return streamingRepository.findAll();
    }

    public Optional<Streaming> findById(int id) {
        return streamingRepository.findById((long) id);
    }

    public Streaming save(Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public Streaming update(Streaming streaming) {
        return streamingRepository.save(streaming);
    }

    public void deleteById(long id) {
        streamingRepository.deleteById(id);
    }

}
