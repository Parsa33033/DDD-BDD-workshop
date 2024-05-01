package org.example.framework.aggregate;

import org.example.framework.mapper.DataTransferObjectMapper;

public interface AggregateRoot<O, D> extends DataTransferObjectMapper<D> {

}
