package sail.study.redis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderInfo {

    private String productName;
    private Integer amount;
    private Long timeStamp;
}
