package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Feedback extends AbstractPersistable<Long>  {

private String content;
private Boolean adm;
private Boolean audience;

    public Feedback() {
        super();
    }

    public Feedback(String content) {
        this();
        this.content = content;
        this.adm = false;
        this.audience = false;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Boolean getAdm() {
        return adm;
    }

    public void setAdm(Boolean adm) {
        this.adm = adm;
    }
    
    public Boolean getAudience() {
        return audience;
    }

    public void setAudience(Boolean audience) {
        this.audience = audience;
    }    
    
    
}