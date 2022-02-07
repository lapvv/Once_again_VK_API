import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfo2 {

    private Integer id;
    private String home_town;
    private String status;
    private String first_name;
    private String last_name;
    private String bdate;
    private Integer bdate_visibility;
    private String phone;
    private Integer relation;
    private Integer sex;
}
