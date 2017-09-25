package database;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class GetSetWorldRecord {
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> collection;

    public GetSetWorldRecord() {
        if(ConnectionInfo.IS_DATABASE_CONNECTION){
            try {
                // connect to mongodb service
                MongoClient mongoClient = new MongoClient(ConnectionInfo.DB_ADDRESS, ConnectionInfo.DB_PORT);

                // connect to mongodb database
                this.mongoDatabase = mongoClient.getDatabase(ConnectionInfo.DB_NAME);
                // System.out.println("Connect to database successfully");

                /**
                 * connect to default collection
                 * */
                // get collection "worldRecord" first
                this.collection = this.getCollection(ConnectionInfo.COLLECTION_NAME);
                MongoCursor<Document> mongoCursor = this.findAllDocuments();
                // if document exists then do nothing
                if (mongoCursor.hasNext()) {
                    // int oldValue = this.getDocumentValue();
                    // System.out.println("current value:==" + oldValue);
                    /* then update document for test */
                    // this.updateDocument(oldValue, 1400);
                    // System.out.println("new value:==" + this.getDocumentValue());
                } else {
                    // create collection
                    this.createCollection(ConnectionInfo.COLLECTION_NAME);
                    this.collection = this.getCollection(ConnectionInfo.COLLECTION_NAME);
                    // insert document which value is 0 for initial
                    this.insertManyDocument(new Document(ConnectionInfo.DOCUMENT_KEY_NAME, 0));
                    System.out.println("initial document value:==" + this.getDocumentValue());
                }
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }

    // constructor with user name and password
    public GetSetWorldRecord(String userName, String password) {
        try {
            ServerAddress serverAddress = new ServerAddress(ConnectionInfo.DB_ADDRESS, ConnectionInfo.DB_PORT);
            List<ServerAddress> addrs = new ArrayList<>();
            addrs.add(serverAddress);

            MongoCredential credential = MongoCredential.createScramSha1Credential(
                    userName,
                    ConnectionInfo.DB_NAME,
                    password.toCharArray()
            );
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(credential);

            MongoClient mongoClient = new MongoClient(addrs, credentials);

            this.mongoDatabase = mongoClient.getDatabase(ConnectionInfo.DB_NAME);
            System.out.println("Connect to database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void createCollection(String collectionName) {
        try {
            this.mongoDatabase.createCollection(collectionName);
            System.out.println("collection " + collectionName + " create success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    private void insertManyDocument(Document document) {
        try {
            List<Document> documents = new ArrayList<>();
            documents.add(document);
            this.collection.insertMany(documents);
            System.out.println("document insert success");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private MongoCursor<Document> findAllDocuments() {
        FindIterable<Document> findIterable = this.collection.find();
        return findIterable.iterator();
    }

    public void updateDocument(int oldValue, int newValue) {
        this.collection.updateMany(
                Filters.eq(ConnectionInfo.DOCUMENT_KEY_NAME, oldValue),
                new Document("$set", new Document(ConnectionInfo.DOCUMENT_KEY_NAME, newValue))
        );
    }

    public int getDocumentValue() {
        return (int) this.findAllDocuments().next().get(ConnectionInfo.DOCUMENT_KEY_NAME);
    }

}