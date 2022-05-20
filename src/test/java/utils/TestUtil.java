package utils;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;

public class TestUtil {
    public static List<EntityResponse> getEntityResponses(DungeonResponse dungeonResponse, String type) {
        return dungeonResponse.getEntities().stream().filter(e->(e.getType().equals(type))).collect(Collectors.toList());
    }
}
