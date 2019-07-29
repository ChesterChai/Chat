package cn.sinjinsong.chat.server.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DaoUser {
    private String userName;
    private String password;
    private Date registerTime;
}
