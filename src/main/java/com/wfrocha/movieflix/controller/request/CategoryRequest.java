package com.wfrocha.movieflix.controller.request;

import lombok.Builder;

@Builder
public record CategoryRequest(String name) {
}
