package pt.eden.hbs.web.rest;

import pt.eden.hbs.common.entity.SnapshotExt;

import java.util.List;

public class ServerResponse {

    private List<SnapshotExt> snapshotList;

    public List<SnapshotExt> getSnapshotList() {
        return snapshotList;
    }

    public void setSnapshotList(List<SnapshotExt> snapshotList) {
        this.snapshotList = snapshotList;
    }
}
