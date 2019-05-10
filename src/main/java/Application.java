import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class Application {

    //The config parameters for the connection
    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9201;
    private static final String SCHEME = "http";

    private static RestHighLevelClient restHighLevelClient;
    private static ObjectMapper objectMapper = new ObjectMapper();

//    private static Gson gson = new Gson();

    private static final String INDEX = "itemdata";
    private static final String TYPE = "item";


    /**
     * Implemented Singleton pattern here
     * so that there is just one connection at a time.
     * @return RestHighLevelClient
     */
    private static synchronized RestHighLevelClient makeConnection() {

        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME),
                            new HttpHost(HOST, PORT_TWO, SCHEME)));
        }

        return restHighLevelClient;
    }

    private static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }

    private static Item insertItem(Item item){
        item.setId(item.getId());
        Map<String, Object> dataMap = item.toDataMap();
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, item.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest);
        } catch(ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (IOException ex){
            ex.getLocalizedMessage();
        }
        return item;
    }

    private static Item getItemById(String id){
    GetRequest getItemRequest = new GetRequest(INDEX, TYPE, id);
    GetResponse getResponse = null;
    try {
        getResponse = restHighLevelClient.get(getItemRequest);
    } catch (IOException e){
        e.getLocalizedMessage();
    }
    return getResponse != null ?
            objectMapper.convertValue(getResponse.getSourceAsMap(), Item.class) : null;
    }

    private static Item updateItemById(String id, Item item){
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
                .fetchSource(true);    // Fetch Object after its update
        try {
            String itemJson = objectMapper.writeValueAsString(item);
            updateRequest.doc(itemJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
            return objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Item.class);
        }catch (JsonProcessingException e){
            e.getMessage();
        } catch (IOException e){
            e.getLocalizedMessage();
        }
        System.out.println("Unable to update person");
        return null;
    }

    private static void deleteItemById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
        } catch (IOException e){
            e.getLocalizedMessage();
        }
    }

    private static boolean getSite(String id) {
        try {
            readUrl("https://api.mercadolibre.com/sites/" + id);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static boolean getCategories(String id) {
        try {
            readUrl("https://api.mercadolibre.com/categories/" + id);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static String readUrl(String urlString) throws IOException{

        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            int read = 0;
            StringBuffer buffer = new StringBuffer();
            char[] chars = new char[1024];
            while((read = reader.read(chars)) != -1){
                buffer.append(chars, 0, read);
            }
            return buffer.toString();
        } finally {
            if (reader != null){
                reader.close();
            }
        }
    }

    private static boolean validar(String site_id, String category_id) {
        boolean siteExists = getSite(site_id);
        boolean categoryExists = getCategories(category_id);
        if (siteExists && categoryExists){
            return true;
        } else {
            if (!siteExists) {
                System.out.println("El item no fue agregado porque no pertenece a un site valido.");
            }
            if (!categoryExists) {
                System.out.println("El item no fue agregado porque no pertenece a una categoria valida.");
            }
            return false;
        }
    }

    public static void main(String[] args) throws IOException {

        makeConnection();

        System.out.println("Agregando nuevo item...");
        Item item = new Item();
        item.setId("1");
        item.setSite_id("MLA");
        item.setCategory_id("MLA1055");
        item.setTitle("Titulo 1");
        if (validar(item.getSite_id(), item.getCategory_id())){
            item = insertItem(item);
            System.out.println("ITEM AGREGADO: ");
            System.out.println(item.toString());
        }
        System.out.println("Obteniendo item...");
        Item itemFromDB = getItemById(item.getId());
        if (itemFromDB != null) {
            System.out.println(itemFromDB.toString());
        }

        System.out.println("Cambiando titulo al item...");
        item.setTitle("Titulo 2");
        if (validar(item.getSite_id(), item.getCategory_id())) {
            item = updateItemById(item.getId(), item);
            System.out.println(item.toString());
        }

        System.out.println("Obteniendo item...");
        itemFromDB = getItemById(item.getId());
        if (itemFromDB != null) {
            System.out.println(itemFromDB.toString());
        }

        System.out.println("Eliminando ITEM");
        deleteItemById(itemFromDB.getId());
        System.out.println("Item Deleted");

        closeConnection();
    }
}