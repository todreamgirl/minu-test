package com.minutest.model;

import com.minutest.model.ClienteModel.Client;

public class ClienteResponse {
	private Client client;
    private String message;

    public ClienteResponse(Client client, String message) {
        this.client = client;
        this.message = message;
    }

    public ClienteResponse() {
    }

    public ClienteModel getClient() {
        return client;
    }

    public void setClient(ClienteModel client) {
        this.client = client;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

