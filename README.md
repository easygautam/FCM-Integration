# FCM-Integration
This is a demo application to integrate firebase messaging in android application.

## Follow the steps
1. Register application on firebase.
2. Integrate ```MyFirebaseMessagingService``` class in your application.
3. Send Notification


```curl
curl -X POST \
  https://fcm.googleapis.com/fcm/send \
  -H 'Content-Type: application/json' \
  -d '{
  "to": "d-_3JxzXTPU:APA91bF-oG1TkkTGtXKFH4ZnHCYlrDcZIFPAM_ZjjfwhfDZZNUodTOD8HtK38sCkHiet44ZrxwwkRiQJ_4Qa5HkVLewLZqGhP1AmuyzDpjxcnQJFIlJBfNHkLZihYFDtr0Xm6_NJxV_5",
  "data": {
    "title": "This is a Firebase Cloud",
    "text": "This is a Firebase Cloud ",
    "image": "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQreAJGK8fB2nA_WExGjJRAz9tZW83TMhdJZrr7FWCDdVGUG5CY"
   }
}'

```
