
package com.myApk.model;

import java.io.Serializable;

/**
 * 描述:Banner实体类
 * 
 * @author liux
 * @since 2014-6-17 上午09:39:19
 */
public class BannerInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;

    private String imageurl;

    private long gameid;
    
    private String linkurl;
    
    public BannerInfo(){
    	
    }
    public BannerInfo(String title, String imageurl, long gameid, String linkurl) {
		super();
		this.title = title;
		this.imageurl = imageurl;
		this.gameid = gameid;
		this.linkurl = linkurl;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public long getGameid() {
        return gameid;
    }

    public void setGameid(long gameid) {
        this.gameid = gameid;
    }
    
    public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
