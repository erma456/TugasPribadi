package id.sch.smktelkom_mlg.privateassignment.xirpl616.tugaspribadi;

/**
 * Created by Khofi Muffin on 15/05/2017.
 */

import java.io.Serializable;

public class OfflineListItem implements Serializable {
    public String imageUrl;
    public String head;
    // private String desc;

    public OfflineListItem(String imageUrl, String head) {
        this.imageUrl = imageUrl;
        this.head = head;

    }}
