# Stage 1: Compile and build Angular codebase
FROM node:22.14.0-slim AS build

WORKDIR /usr/local/app
COPY ./ /usr/local/app/

# Install dependencies and build the application for production
RUN npm install
RUN npm run build

# Stage 2: Serve app with nginx server
FROM nginx:1.26.3

# Copy the build output to replace the default nginx contents.
COPY --from=build /usr/local/app/dist/frontend/browser /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 443
