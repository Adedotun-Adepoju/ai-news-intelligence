# 🧠 SWEN AI-Enriched News Pipeline

An **AI-powered news enrichment platform** inspired by **SWEN's architecture** — transforming plain text news into **contextually intelligent, media-rich** stories using **Qwen LLM**, **Bing News API**, **YouTube Data API**, and **Wikipedia enrichment**.

## 🌐 Base URL
All API endpoints are relative to the following base URL:
```
https://ai-news-intelligence-production.up.railway.app/api/v1
```

---

## 🚀 Live Prototype
🔗 **URL:** [https://ai-news-intelligence-production.up.railway.app/api/v1/news/african-football](https://ai-news-intelligence-production.up.railway.app/api/v1/news/african-football)  
Returns clean JSON output directly viewable in-browser. (Right-click and select "Open in new tab" to keep this page open)

> **Note:** This endpoint returns cached results. To fetch fresh news, use the search endpoint below.

## 🔍 Search Endpoint

- **Search for news articles**:
  ```
  GET /news/search?q={keyword}
  ```
  Fetches the latest news for the specified search keyword. 
  
  > **Important:** This endpoint triggers a new search and saves the results in the database to optimize token usage. Subsequent requests for the same keyword will return the cached results. Use this endpoint when you need fresh data.

---

## 🧩 Alignment with SWEN's Architecture

| SWEN Architecture Layer | My Implementation                                                                                                                    |
|--------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| **Ingestion** | Pulls text-only news from **News API**, including `title`, `content`, `description`, `source_url`, `image_url`, and `published_at`.  |
| **AI Transformation** | Uses **Qwen LLM** for summarization, tagging, relevance scoring, and contextual reasoning.                                           |
| **Media Enrichment** | Fetches **real, relevant video URLs** via **YouTube Data API** based on AI-generated search terms.                                   |
| **Contextual Intelligence** | Adds **Wikipedia snippets**, **social sentiment**, **search trend data**, and **geo-coordinates** (for ArcGIS integration).          |
| **Storage & Delivery** | Returns structured **JSON output** (per SWEN spec) via `/api/v1/news/{searchVariable}` endpoint. Saves enriched output to database.  |
| **Infrastructure-as-Code** | Terraform config enables deployment to both **AWS** and **Alibaba Cloud** with containerized microservices and no hardcoded secrets. |

---

## 🏗️ Tech Stack

- **Backend Framework:** Spring Boot 3.x (Java 21)
- **Database:** MySQL
- **AI Model:** Qwen LLM via DashScope API (Alibaba Cloud)
- **External APIs:**
  - News API – content ingestion
  - YouTube Data API – related video retrieval
- **Clouds:** AWS + Alibaba Cloud ready
- **IaC:** Terraform modules for multi-cloud deployment
- **Build Tool:** Maven
---

## 🛠️ Setup Instructions

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.8+
- API keys for:
  - News API
  - Qwen (DashScope)
  - YouTube Data API

### Configuration

Update the appropriate properties file based on your environment:

1. For development:
   ```bash
   src/main/resources/application-development.properties
   ```

2. For production:
   ```bash
   src/main/resources/application-production.properties
   ```

### Configuration Properties

Update the following required properties in the respective file:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://your_db_host/your_db_name
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

# API Keys (replace with your actual keys)
news.api.key=your_news_api_key
qwen.api.key=your_qwen_api_key
youtube.api.key=your_youtube_api_key
```

### 📝 Obtaining API Keys

#### 1. News API Key
- **Sign Up**: [News API Registration](https://newsapi.org/register)
- **Steps**:
  1. Register for a free account
  2. Verify your email
  3. Get your API key from the dashboard

#### 2. Qwen (DashScope) API Key
- **Sign Up**: [Alibaba Cloud DashScope](https://dashscope.aliyun.com/)
- **Steps**:
  1. Create an account
  2. Navigate to API Key Management
  3. Create a new API key

#### 3. YouTube Data API Key
- **Sign Up**: [Google Cloud Console](https://console.cloud.google.com/)
- **Steps**:
  1. Create a new project
  2. Enable YouTube Data API v3
  3. Go to Credentials
  4. Create API key
  5. (Recommended) Restrict the API key for security

After obtaining all API keys, add them to the appropriate configuration file.

To run with a specific profile, use:
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=development  # or production
```

## 🚀 Running the Application

1. Start the application:
   ```bash
   mvn clean spring-boot:run
   ```

2. Available Endpoints:
   - **Search for news articles**:
     ```
     GET /news/search?q={keyword}
     ```
     Fetches the latest news for the specified search keyword.


## 🤖 Qwen & AI Integrations

### How Qwen Is Used
- **Summarization:** Converts long-form articles into concise factual summaries (1–2 sentences).
- **Tagging:** Extracts 3–5 topic-relevant hashtags.
- **Relevance Scoring:** Computes a float (0.0–1.0) based on African context relevance.
- **Media Enrichment**:Generates optimized search queries for YouTube API based on article content
- **Justification:** Explains why certain media were chosen, including video selection criteria
- **Contextual Enrichment:**
  - Wikipedia snippet
  - Social sentiment: Analysis of public opinion from social media platforms, showing the overall sentiment distribution and engagement levels
  - Search trend: Indicates the relative popularity and growth of related search terms over time.
  - Geo context (lat, lng, and map_url for ArcGIS display)
  - Related topics and entities for enhanced content discovery

### Other Tools Used
- **YouTube API:** Fetches educational or journalistic videos relevant to article topics.

## 🎥 Media Verification

✅ **Images** — fetched live from News API, not placeholders.  
✅ **Videos** — retrieved via YouTube Data API searches tied to article keywords.

All media URLs are validated and publicly accessible.


## 🏁 Summary

This project demonstrates how SWEN's AI architecture can be implemented as a modular, production-grade system:
- Cloud-native  
- AI-driven  
- Media-rich
