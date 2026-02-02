package model;

import java.util.Set;

public class Task {
    public String name;
    public String pic;
    public Status status;
    public String desc;
    public String startDate;
    public String endDate;
    public int urgency;
    public Set<String> tags;

    public Task(
        String name,
        String pic,
        Status status,
        String desc,
        String startDate,
        String endDate,
        int urgency,
        Set<String> tags
    ) {
        this.name = name;
        this.pic = pic;
        this.status = status;
        this.desc = desc;
        this.startDate = startDate;
        this.endDate = endDate;
        this.urgency = urgency;
        this.tags = tags;
    }
}
