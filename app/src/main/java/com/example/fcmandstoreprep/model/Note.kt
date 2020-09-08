package com.example.fcmandstoreprep.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Note (
    var userName: String = "",
    var title: String = "",
    var description: String = "",
    var userId: String = "",
    @ServerTimestamp var timestamp: Date? = null
)