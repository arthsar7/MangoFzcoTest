# MangoFzcoTest - Android Mobile Application

## Overview

This project is a test Android mobile application developed as part of a technical assessment. The goal is to demonstrate knowledge in Android development using Kotlin. The project uses Jetpack Compose for UI, Hilt for dependency injection, and Retrofit for network communication. JWT tokens are used for authentication.

### Features

- **Authentication**: Phone number login with SMS code confirmation.
- **Registration**: Create a new user account with a username.
- **Profile**: View and edit user profile information.
- **Chat UI**: Basic layout for a chat interface (no functional backend for chat).

## Screens

1. **Authentication Screen**:
    - Enter phone number with a mask, country code, and flag.
    - Confirm phone number using a 6-digit SMS code (test code: `133337`).
    - JWT access and refresh token handling after successful authentication.

2. **Registration Screen**:
    - Input username and name.
    - Create a new user account.
    - JWT access and refresh tokens are saved for session handling.

3. **Profile Screen**:
    - View user details: avatar, phone number, nickname, city, birthday, zodiac sign, and about me section.
    - Fetch profile data using the GET `/api/v1/users/me/` request.
    - Data is cached locally to avoid redundant requests.

4. **Edit Profile Screen**:
    - Edit user data except for phone and username.
    - Upload or change avatar (base64-encoded).
    - Save changes locally and send data using the PUT `/api/v1/users/me/` request.

5. **Chat Screen**:
    - Basic chat UI layout with a message list and input field.

## Tech Stack

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For modern declarative UI.
- **Hilt**: Dependency injection framework for Android.
- **Retrofit**: For network communication.
- **Room**: Local database for caching data.
- **Coil**: Image loading library for avatar display.
- **DataStore**: For storing JWT tokens.
- **JWT (JSON Web Token)**: For handling authentication tokens.

## Project Structure

- `data`: Contains repositories, network services, and API-related classes.
- `model`: Contains the data models.
- `di`: Dependency injection setup using Hilt.
- `presentation`: Contains all the UI components built with Jetpack Compose.

## API Endpoints

- **POST** `/api/v1/users/send-auth-code/`: Send phone number for SMS code verification.
- **POST** `/api/v1/users/check-auth-code/`: Verify SMS code and return access and refresh tokens.
- **POST** `/api/v1/users/register/`: Create a new user account.
- **GET** `/api/v1/users/me/`: Fetch current user profile.
- **PUT** `/api/v1/users/me/`: Update user profile (including avatar upload).

## Error Handling

- Handles network errors and displays appropriate error messages.
- Automatically refreshes the JWT token when it expires (via `/api/v1/users/refresh-token/`).

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/arthsar7/MangoFzcoTest.git
