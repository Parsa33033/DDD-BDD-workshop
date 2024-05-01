package org.example.test.mock.config;

import org.example.framework.repository.Repository;

public interface RepositoryMock<R extends Repository> {
    R repository();
}
