package org.its.db.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ListWifiConnection implements Serializable {

    private List<WifiConnection> connections;

    public ListWifiConnection() {
    }

    public ListWifiConnection(List<WifiConnection> connections) {
        this.connections = connections;
    }

    public List<WifiConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<WifiConnection> connections) {
        this.connections = connections;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListWifiConnection that = (ListWifiConnection) o;
        return Objects.equals(connections, that.connections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connections);
    }
}
