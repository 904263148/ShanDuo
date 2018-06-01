package com.yapin.shanduo.model.entity;

import java.util.List;

/**
 * 作者：L on 2018/6/1 0001 09:12
 */
public class CreditInfo {

    private int totalpage;
    private int page;
    private Head maps;

    private List<Credit> list;

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Head getMaps() {
        return maps;
    }

    public void setMaps(Head maps) {
        this.maps = maps;
    }

    public List<Credit> getList() {
        return list;
    }

    public void setList(List<Credit> list) {
        this.list = list;
    }

    public static class Head {
        private String head_portrait_id;
        private int reputation , id;

        public String getHead_portrait_id() {
            return head_portrait_id;
        }

        public void setHead_portrait_id(String head_portrait_id) {
            this.head_portrait_id = head_portrait_id;
        }

        public int getReputation() {
            return reputation;
        }

        public void setReputation(int reputation) {
            this.reputation = reputation;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Credit{
        private String mode , activity_name , gender , user_name ,head_portrait_id , id;
        private int birthday , vipGrade;

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getHead_portrait_id() {
            return head_portrait_id;
        }

        public void setHead_portrait_id(String head_portrait_id) {
            this.head_portrait_id = head_portrait_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getVipGrade() {
            return vipGrade;
        }

        public void setVipGrade(int vipGrade) {
            this.vipGrade = vipGrade;
        }

        List<CreditItem> scoreList;

        public List<CreditItem> getScoreList() {
            return scoreList;
        }

        public void setScoreList(List<CreditItem> scoreList) {
            this.scoreList = scoreList;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public int getBirthday() {
            return birthday;
        }

        public void setBirthday(int birthday) {
            this.birthday = birthday;
        }
    }

}
