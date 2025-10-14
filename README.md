# 🧠 SWEN AI-Enriched News Pipeline

An **AI-powered news enrichment platform** inspired by **SWEN's architecture** — transforming plain text news into **contextually intelligent, media-rich** stories using **Qwen LLM**, **Bing News API**, **YouTube Data API**, and **Wikipedia enrichment**.

---

## 🚀 Live Prototype
🔗 **URL:** [https://swen-test.fly.dev/api/v1/news/example](#)  
Returns clean JSON output directly viewable in-browser.

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

Create an `application.properties` or `application.yml` file:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/swen
spring.datasource.username=root
spring.datasource.password=secret

# API Keys
bing.api.key=your_bing_api_key
qwen.api.key=your_qwen_api_key
youtube.api.key=your_youtube_api_key

# App Config
spring.application.name=swen-backend
server.port=8080
```

## 🚀 Running the Application

1. Start the application:
   ```bash
   mvn clean spring-boot:run
   ```

2. Available Endpoints:
   - **Search for news articles**:
     ```
     GET /api/v1/news/search?q={keyword}
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
  - Social sentiment (e.g., "74% positive mentions on X in last 24h")
  - Search trend (e.g., "'SA renewables' +150% this week")
  - Geo context (lat, lng, and map_url for ArcGIS display)
  - Related topics and entities for enhanced content discovery

### Other Tools Used
- **YouTube API:** Fetches educational or journalistic videos relevant to article topics.

## 🎥 Media Verification

✅ **Images** — fetched live from News API, not placeholders.  
✅ **Videos** — retrieved via YouTube Data API searches tied to article keywords.

All media URLs are validated and publicly accessible.

## 🧱 Infrastructure-as-Code (Terraform)

Multi-cloud ready setup for AWS + Alibaba Cloud.

Creates:
- EKS/ACK Kubernetes clusters
- Container registry
- Secrets management (Qwen, Bing, YouTube keys)
- CI/CD pipelines via GitHub Actions or GitLab CI

Example structure:
```
infra/
├── main.tf
├── variables.tf
├── outputs.tf
└── terraform.tfvars
```

## 🏁 Summary

This project demonstrates how SWEN's AI architecture can be implemented as a modular, production-grade system:
- Cloud-native  
- AI-driven  
- Media-rich
