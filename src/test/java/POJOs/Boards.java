package POJOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boards {

        private String name;
        private String desc;
        private ArrayList descData;
        private boolean closed;
        private Object dateClosed;
        private String idOrganization;
        private Object idEnterprise;
        private Object limits;
        private Object pinned;
        private String shortLink;
        private ArrayList powerUps;
        private Date dateLastActivity;
        private ArrayList idTags;
        private Object datePluginDisable;
        private Object creationMethod;
        private Object ixUpdate;
        private boolean enterpriseOwned;
        private String idBoardSource;
        private String idMemberCreator;
        private String id;
        private boolean starred;
}
