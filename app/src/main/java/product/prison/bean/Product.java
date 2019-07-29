package product.prison.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhu on 2017/9/18.
 */

public class Product implements Serializable {
    private int id;

    private String name;

    private int status;

    private long createDate;

    private List<Lives> lives;

    private List<SingleLives> singleLives;

    private List<Sources> sources;

    private List<LiveApps> liveApps;

    private List<VodApps> vodApps;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public long getCreateDate() {
        return this.createDate;
    }

    public void setLives(List<Lives> lives) {
        this.lives = lives;
    }

    public List<Lives> getLives() {
        return this.lives;
    }

    public void setSingleLives(List<SingleLives> singleLives) {
        this.singleLives = singleLives;
    }

    public List<SingleLives> getSingleLives() {
        return this.singleLives;
    }

    public void setSources(List<Sources> sources) {
        this.sources = sources;
    }

    public List<Sources> getSources() {
        return this.sources;
    }

    public void setLiveApps(List<LiveApps> liveApps) {
        this.liveApps = liveApps;
    }

    public List<LiveApps> getLiveApps() {
        return this.liveApps;
    }

    public void setVodApps(List<VodApps> vodApps) {
        this.vodApps = vodApps;
    }

    public List<VodApps> getVodApps() {
        return this.vodApps;
    }

}
