package org.example.framework.mapper;

public interface Mapper<O, D> extends DomainObjectMapper<O>, DataTransferObjectMapper<D> {

  D toDataTransferObject();

  O toDomainObjectMapper();
}
