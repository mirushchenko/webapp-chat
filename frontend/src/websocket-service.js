let stompClient = null;
let connected = false;

// Используем глобальный Stomp из CDN
const Stomp = window.Stomp;

export function connectWebSocket() {
    const username = document.getElementById('username').value || 'User';
    console.log('🔗 Connecting WebSocket for user:', username);
    
    try {
        // Создаем SockJS соединение
        const socket = new SockJS('http://localhost:8080/ws');
        
        // Создаем STOMP клиент поверх SockJS
        stompClient = Stomp.over(socket);
        
        // Включаем отладку
        stompClient.debug = function(str) {
            console.log('🔧 STOMP Debug:', str);
        };
        
        // Подключаемся
        stompClient.connect({},
            function(frame) {
                console.log('✅ WebSocket Connected! Frame:', frame);
                connected = true;
                updateStatus('Connected', true);
                
                // Подписываемся на получение сообщений
                stompClient.subscribe('/topic/messages', function(message) {
                    console.log('📨 Message received:', message.body);
                    const chatMessage = JSON.parse(message.body);
                    displayMessage(chatMessage);
                });
                
                // Активируем кнопку отправки
                document.getElementById('sendBtn').disabled = false;
                
                // Сообщаем серверу о подключении
                stompClient.send("/app/chat.join", {}, JSON.stringify({
                    username: username,
                    type: 'JOIN'
                }));
                
                console.log('🎉 Ready to chat!');
            },
            function(error) {
                console.error('❌ WebSocket connection error:', error);
                connected = false;
                updateStatus('Connection Failed', false);
                document.getElementById('sendBtn').disabled = true;
            }
        );
        
    } catch (error) {
        console.error('❌ Connection setup error:', error);
        updateStatus('Error: ' + error.message, false);
    }
}

export function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
        console.log('🔌 WebSocket disconnected');
    }
    connected = false;
    updateStatus('Disconnected', false);
    document.getElementById('sendBtn').disabled = true;
}

export function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const usernameInput = document.getElementById('username');
    
    const message = messageInput.value;
    const username = usernameInput.value || 'User';
    
    if (message.trim() && connected && stompClient) {
        console.log('📤 Sending message:', message);
        
        const chatMessage = {
            user: username,
            content: message
        };
        
        // Отправляем сообщение через WebSocket
        stompClient.send("/app/chat.send", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
        
        // Показываем наше сообщение локально сразу
        displayMessage({
            user: username,
            content: message,
            timestamp: 'sending...'
        });
    } else {
        console.log('⚠️ Cannot send: connected=', connected, 'stompClient=', stompClient);
    }
}

function updateStatus(message, isConnected) {
    const statusElement = document.getElementById('status');
    if (statusElement) {
        statusElement.textContent = message;
        statusElement.className = `status ${isConnected ? 'connected' : 'disconnected'}`;
    }
}

function displayMessage(message) {
    const messagesElement = document.getElementById('messages');
    if (messagesElement) {
        const messageElement = document.createElement('div');
        messageElement.className = 'message';
        messageElement.innerHTML = `
            <strong>${message.user}</strong>: ${message.content}
            <br><small>${message.timestamp || ''}</small>
        `;
        messagesElement.appendChild(messageElement);
        messagesElement.scrollTop = messagesElement.scrollHeight;
    }
}
