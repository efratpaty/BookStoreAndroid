package StoreJavaClass;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by aviga_000 on 09/11/2015.
 */
public class Complains implements Serializable
{
    private static final AtomicInteger count = new AtomicInteger(1000);//variable for auto generating id
    private int complaintId;
    private long complainantId;
    private long defendantId;//the one you complain about
    private String complaint;
    //constructor
    public Complains( long complainantId, long defendantId, String complaint) {
        this.complaintId = count.incrementAndGet();
        this.complainantId = complainantId;
        this.defendantId = defendantId;
        this.complaint = complaint;
    }

    //getters&setters
    public int getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(int complaintId) {
        this.complaintId = complaintId;
    }

    public long getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(long complainantId) {
        this.complainantId = complainantId;
    }

    public long getDefendantId() {
        return defendantId;
    }

    public void setDefendantId(long defendantId) {
        this.defendantId = defendantId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

}
