package com.agstar.testapp.model.fb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("height")
        @Expose
        private Long height;
        @SerializedName("is_silhouette")
        @Expose
        private Boolean isSilhouette;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("width")
        @Expose
        private Long width;

        public Long getHeight() {
            return height;
        }

        public void setHeight(Long height) {
            this.height = height;
        }

        public Boolean getIsSilhouette() {
            return isSilhouette;
        }

        public void setIsSilhouette(Boolean isSilhouette) {
            this.isSilhouette = isSilhouette;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Long getWidth() {
            return width;
        }

        public void setWidth(Long width) {
            this.width = width;
        }

    }
}