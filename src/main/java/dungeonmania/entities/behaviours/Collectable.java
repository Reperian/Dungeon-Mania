package dungeonmania.entities.behaviours;

import dungeonmania.response.models.ItemResponse;

public interface Collectable {
    
    /**
     * Returns the Item response
     * @return ItemResponse
     */
    public ItemResponse getItemResponse();

    /**
     * Collects the entity
     * @return
     */
    public boolean collect();
}
