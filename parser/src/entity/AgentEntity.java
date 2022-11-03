package entity;

import java.time.OffsetDateTime;

/**
 *
 * @author Zuzana Žillová
 */
public class AgentEntity {
    
    private int id;
    private OffsetDateTime from;
    private OffsetDateTime to;

    public AgentEntity(int id, OffsetDateTime from, OffsetDateTime to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OffsetDateTime getFrom() {
        return from;
    }

    public void setFrom(OffsetDateTime from) {
        this.from = from;
    }

    public OffsetDateTime getTo() {
        return to;
    }

    public void setTo(OffsetDateTime to) {
        this.to = to;
    }
    
}
