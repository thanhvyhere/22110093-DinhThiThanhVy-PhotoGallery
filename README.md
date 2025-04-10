﻿# 22110093-DinhThiThanhVy-PhotoGallery

# Photo Gallery App - Feature Overview

## Core Features

### Grid View with Clickable Images
- Implement a grid layout using `LazyVerticalGrid` in Jetpack Compose.
- Each cell contains a photo thumbnail.
- Tap on an image to open a full-screen view.
- Concepts introduced:
  - Efficient list handling
  - Clickable image components in Compose

### Full Photo View with Navigation Buttons
- Display a single full-sized photo.
- Include "Previous" and "Next" buttons for navigation.
- Teaches:
  - State management across multiple screens
  - Basic UI navigation in Compose

### Floating Action Button (FAB)
- Add a floating action button for:
  - Taking photos via camera
  - Selecting from gallery
  - Accessing settings
- Covers:
  - FloatingActionButton implementation
  - Launching activity results
  - User interaction design

### Gesture Support
- Enable common gestures:
  - Swipe left/right to navigate in full photo view
  - Pinch to zoom (optional bonus)
  - Long press on a grid photo to:
    - Mark as favorite
    - Show delete dialog
- Introduces:
  - Gesture detection with `pointerInput` and `detectTapGestures`
  - Custom interaction handling

## Advanced Elements

### Architecture and State Management
- Apply MVVM architecture:
  - ViewModel + LiveData or StateFlow
- Decouple UI from logic using best practices
- Benefits:
  - Scalable, testable, and maintainable codebase

### Performance Optimization
- Use:
  - Lazy loading with `LazyVerticalGrid`
  - Coil for efficient image loading and caching
- Outcome:
  - Smooth performance on large datasets
  - Battery and memory optimization

### Custom Animations and Responsiveness
- Add:
  - Transitions between screens (e.g., fade, zoom)
  - Button click animations
- Make UI responsive across:
  - Phones
  - Tablets
- Learn:
  - `animate*AsState` APIs
  - Adaptive layouts using `BoxWithConstraints`, `ConstraintLayout`, etc.

### Device Integration (Optional)
- Use:
  - Camera intent for taking photos
  - Room database to persist photo metadata (e.g., favorites, timestamps)
- Adds:
  - Real-world development experience
  - Data persistence and permissions handling

## Learning Outcomes

By completing this project, students will:

- Build a fully functional media app using Jetpack Compose
- Master gesture handling, navigation, and custom UI components
- Understand and apply MVVM, performance best practices, and animations
- Learn to integrate device features like camera and local database storage
