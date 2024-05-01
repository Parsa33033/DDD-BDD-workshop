package org.example.service.command.customerorder.domain.aggregate;

import org.example.dto.aggregate.CustomerOrderData;
import org.example.framework.aggregate.AggregateRoot;
import org.example.framework.model.DomainObjectBuilder;
import org.example.framework.result.Result;
import org.example.outgoing.dto.change.CustomerOrderChange;
import org.example.service.command.customerorder.domain.error.CustomerOrderError;

public class CustomerOrder implements AggregateRoot<CustomerOrder, CustomerOrderData> {



    public CustomerOrder(CustomerOrderBuilder builder) {
        //TODO
    }

    public Result<CustomerOrderChange, CustomerOrderError> createOrder() {
        // TODO
        throw new RuntimeException("not implemented");
    }

    @Override
    public CustomerOrderData toDataTransferObject() {
        //TODO use getDomainObject/getDomainObjectSet to build an ImmutableCustomerOrderData
//        return ImmutableCustomerOrderData
//                .builder()
//                .customerIdentifier(getDomainObject())
//                .cart(getDomainObject())
//                .order(getDomainObject())
//                .products(getDomainObjectSet())
//                .build();
        throw new RuntimeException("not implemented");
    }

    public static CustomerOrder fromDataTransferObject(CustomerOrderData customerOrderData) {
        return new CustomerOrderBuilder(customerOrderData).build();
    }

    private static class CustomerOrderBuilder extends
            DomainObjectBuilder<CustomerOrder, CustomerOrderData> {

        //TODO

        public CustomerOrderBuilder(CustomerOrderData customerOrderData) {
            super(customerOrderData);
        }

        @Override
        protected CustomerOrder build() {
            //TODO
            return new CustomerOrder(this);
        }
    }
}
