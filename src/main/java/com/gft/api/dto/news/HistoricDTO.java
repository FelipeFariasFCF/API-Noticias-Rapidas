package com.gft.api.dto.news;

import com.gft.api.entities.Historic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private String user;
    private String tag;
    private LocalDateTime dateAccess;

    public HistoricDTO(Historic historic) {
        this.user = historic.getUser().getName();
        this.tag = historic.getTag().getName();
        this.dateAccess = historic.getDateAccess();
    }
}