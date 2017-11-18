package bd.com.thedebuggers.gontobbo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ash on 10/13/2017.
 */

public interface FirebaseHelper {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();



}
