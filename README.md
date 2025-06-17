# Groovy-backend

This project is backend of KMP music player application. Now it in progress, sometime it enter into release version. 

## Track Endpoints

### GET /api/tracks
Get all tracks
- Response: List of tracks
- Example: `GET /api/tracks`

### GET /api/tracks/{id}
Get track by ID
- Parameters:
  - `id`: Track ID
- Response: Track object
- Example: `GET /api/tracks/123`

### GET /api/tracks/album/{albumId}
Get tracks by album ID
- Parameters:
  - `albumId`: Album ID
- Response: List of tracks
- Example: `GET /api/tracks/album/456`

### GET /api/tracks/artist/{artistId}
Get tracks by artist ID
- Parameters:
  - `artistId`: Artist ID
- Response: List of tracks
- Example: `GET /api/tracks/artist/789`

### GET /api/tracks/liked/{userId}
Get liked tracks for a user
- Parameters:
  - `userId`: User ID
- Response: List of tracks
- Example: `GET /api/tracks/liked/abc`
