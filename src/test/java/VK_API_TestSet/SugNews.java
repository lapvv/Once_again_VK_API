package VK_API_TestSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SugNews {

    public Integer post_id;

//    public int source_id { get; set; }
//    public int date { get; set; }
//    public bool can_doubt_category { get; set; }
//    public bool can_set_category { get; set; }
//    public string post_type { get; set; }
//    public string text { get; set; }
//    public int marked_as_ads { get; set; }
//    public List<Attachment> attachments { get; set; }
//    public PostSource post_source { get; set; }
//    public Comments comments { get; set; }
//    public Likes likes { get; set; }
//    public Reposts reposts { get; set; }
//    public Views views { get; set; }
//    public bool is_favorite { get; set; }
//    public Donut donut { get; set; }
//    public double short_text_rate { get; set; }
//    public int carousel_offset { get; set; }
//    public int post_id { get; set; }
//    public string type { get; set; }
//    public bool? keep_offline { get; set; }

}
