package vn.ghtk.training.microservices.commons.event;

import vn.ghtk.training.microservices.utilities.id.UUIDUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DomainEvent<T extends EventContent> implements Serializable {
    private static final long serialVersionUID = 1L;
    private EventId id;
    private AggregateId aggregateId;
    private EventType type;
    private IssuerId issuerId;
    private Date occurredOn;
    private T content;

    public DomainEvent(AggregateId aggregateId,
                       EventType type,
                       IssuerId issuerId,
                       Date occurredOn,
                       T content) {
        this.id = new EventId(UUIDUtil.genUUID());
        this.aggregateId = aggregateId;
        this.type = type;
        this.issuerId = issuerId;
        this.occurredOn = occurredOn;
        this.content = content;
    }

    public AggregateId aggregateId() {
        return aggregateId;
    }

    public EventId id() {
        return id;
    }

    public EventType type() {
        return type;
    }

    public IssuerId issuerId() {
        return issuerId;
    }

    public Date occurredOn() {
        return occurredOn;
    }

    public T content() {
        return content;
    }

    @Override
    public String toString() {
        return "DomainEvent{" +
                "id=" + id +
                ", aggregateId=" + aggregateId +
                ", type=" + type +
                ", issuerId=" + issuerId +
                ", occurredOn=" + occurredOn +
                ", content=" + content +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainEvent<?> that = (DomainEvent<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
