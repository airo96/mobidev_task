package ua.i74.movieapp.model.network.response;

import com.squareup.moshi.Json;

import java.util.List;

import ua.i74.movieapp.model.network.PersonNetworkModel;

public class GetPersonWithNameResponse {
    @Json(name = "results")
    private List<PersonNetworkModel> personList;

    public List<PersonNetworkModel> getPersonList() {
        return personList;
    }

    public void setPersonList(List<PersonNetworkModel> personList) {
        this.personList = personList;
    }
}
