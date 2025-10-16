import { connectWebSocket, disconnectWebSocket, sendMessage } from './websocket-service.js';

// Глобальные функции для HTML кнопок
window.connect = connectWebSocket;
window.disconnect = disconnectWebSocket;
window.sendMessage = sendMessage;

// Обработка Enter в поле сообщения
document.getElementById('messageInput')?.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});

console.log('Chat app initialized');