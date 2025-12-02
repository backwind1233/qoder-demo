#!/bin/bash

# Spring Boot Landing Page - Build & Run Script
# This script helps you build and run the application

set -e

echo "=========================================="
echo "  Spring Boot Landing Page"
echo "  Build & Run Script"
echo "=========================================="
echo ""

# Check Java installation
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "   Please install Java 17 or higher"
    echo "   Download from: https://adoptium.net/"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -n 1 | awk -F '"' '{print $2}' | awk -F '.' '{print $1}')
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Error: Java 17 or higher is required"
    echo "   Current version: $JAVA_VERSION"
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"

# Check Maven installation
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "   Please install Maven 3.6 or higher"
    echo "   Download from: https://maven.apache.org/download.cgi"
    exit 1
fi

echo "✅ Maven version: $(mvn -version | head -n 1)"
echo ""

# Parse command line arguments
ACTION=${1:-run}

case $ACTION in
    build)
        echo "📦 Building the application..."
        echo ""
        mvn clean package -DskipTests
        echo ""
        echo "✅ Build complete! JAR file: target/landing-page-1.0.0.jar"
        ;;
    
    test)
        echo "🧪 Running tests..."
        echo ""
        mvn test
        echo ""
        echo "✅ Tests complete!"
        ;;
    
    run)
        echo "🚀 Starting the application..."
        echo ""
        echo "   The application will be available at:"
        echo "   👉 http://localhost:8080"
        echo ""
        echo "   Press Ctrl+C to stop the application"
        echo ""
        mvn spring-boot:run
        ;;
    
    package)
        echo "📦 Building production package..."
        echo ""
        mvn clean package
        echo ""
        echo "✅ Package complete! Run with:"
        echo "   java -jar target/landing-page-1.0.0.jar"
        ;;
    
    clean)
        echo "🧹 Cleaning build artifacts..."
        mvn clean
        echo "✅ Clean complete!"
        ;;
    
    *)
        echo "Usage: $0 [action]"
        echo ""
        echo "Available actions:"
        echo "  run      - Build and run the application (default)"
        echo "  build    - Build the application without tests"
        echo "  test     - Run all tests"
        echo "  package  - Build production package with tests"
        echo "  clean    - Clean build artifacts"
        echo ""
        echo "Examples:"
        echo "  $0              # Run the application"
        echo "  $0 build        # Build without running"
        echo "  $0 test         # Run tests"
        echo "  $0 package      # Create production JAR"
        exit 1
        ;;
esac

echo ""
echo "=========================================="
echo "  Done!"
echo "=========================================="
