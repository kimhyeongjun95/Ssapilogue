FROM node:16.13.2-alpine
WORKDIR /frontend
COPY . /frontend
RUN npm install --legacy-peer-deps
CMD ["npm", "run", "build"]