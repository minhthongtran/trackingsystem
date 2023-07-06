import { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.example.app',
  appName: 'customer-app',
  webDir: 'dist/customer-app',
  server: {
    androidScheme: 'https',
  },
};

export default config;
