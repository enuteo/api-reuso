# ---- Build stage ----
FROM node:20-alpine AS builder
WORKDIR /app

# Install deps (use lockfile if present)
COPY package.json package-lock.json* bun.lockb* ./
RUN npm install --no-audit --no-fund

# Copy source and build
COPY . .
RUN npm run build

# ---- Runtime stage ----
FROM nginx:1.27-alpine AS runtime

# SPA-friendly nginx config (history fallback to index.html)
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Static assets from the build
COPY --from=builder /app/dist /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
