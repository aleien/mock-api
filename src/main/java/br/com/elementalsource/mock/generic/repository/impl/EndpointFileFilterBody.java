package br.com.elementalsource.mock.generic.repository.impl;

import br.com.elementalsource.mock.generic.model.Endpoint;
import br.com.elementalsource.mock.infra.component.CompareJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class EndpointFileFilterBody implements EndpointFileFilter<Optional<String>> {

    private final CompareJson compareJson;

    @Autowired
    public EndpointFileFilterBody(CompareJson compareJson) {
        this.compareJson = compareJson;
    }

    @Override
    public Boolean apply(Endpoint endpoint, Optional<String> request) {
        return request
                .map(requestBody ->
                        endpoint
                                .getRequest()
                                .getBody()
                                .map(expetedBody -> compareJson.isEquivalent(expetedBody, requestBody))
                                .orElse(true)
                )
                .orElse(!endpoint.getRequest().getBody().isPresent());
    }


}
